import sys
import torch
from diffusers import DiffusionPipeline, AutoencoderKL, EulerAncestralDiscreteScheduler, StableDiffusionXLPipeline
from PIL import Image
import os
import boto3

def model_select(model_select_num):
    if model_select_num == 3:
        model_id = "cagliostrolab/animagine-xl-3.1"
        vae = AutoencoderKL.from_pretrained("madebyollin/sdxl-vae-fp16-fix", torch_dtype=torch.float16)
    else:
        model_id = "stabilityai/stable-diffusion-xl-base-1.0"
        vae = None
    return model_id, vae

def model_load(user_select_model, model_id, vae):
    if user_select_model == 3:
        pipeline = StableDiffusionXLPipeline.from_pretrained(
            model_id,
            vae=vae,
            torch_dtype=torch.float16,
            use_safetensors=True,
        )
        pipeline.scheduler = EulerAncestralDiscreteScheduler.from_config(pipeline.scheduler.config)
    else:
        pipeline = DiffusionPipeline.from_pretrained(model_id)
    return pipeline

def lora_select(pipeline, user_select_model):
    if user_select_model == 1:
        pipeline.load_lora_weights("alvdansen/midsommarcartoon", weight_name="araminta_k_midsommar_cartoon.safetensors")
        pipeline.fuse_lora(lora_scale=0.8)
    elif user_select_model == 2:
        pipeline.load_lora_weights("alvdansen/littletinies", weight_name="Little_Tinies.safetensors")
        pipeline.fuse_lora(lora_scale=0.8)
    elif user_select_model == 4:
        pipeline.load_lora_weights("nerijs/pixel-art-xl", weight_name="pixel-art-xl.safetensors")
        pipeline.fuse_lora(lora_scale=0.8)

def upload_to_s3(image_path, bucket_name, s3_key):
    s3 = boto3.client(
        's3',
        aws_access_key_id=os.environ.get('AWS_ACCESS_KEY_ID'),
        aws_secret_access_key=os.environ.get('AWS_SECRET_ACCESS_KEY')
    )
    try:
        s3.upload_file(image_path, bucket_name, s3_key)
        print(f"Image successfully uploaded to {bucket_name}/{s3_key}")
        image_url = f"https://{bucket_name}.s3.amazonaws.com/{s3_key}"
        return image_url
    except Exception as e:
        print(f"Error uploading image: {e}")
        raise

def gen_img(prompt, negative_prompt, img_num, pipeline, user_select_model, bucket_name, username):
    base_prompt = "masterpiece, best quality, "
    base_negative_prompt = "nsfw, ugly, lowres, bad anatomy, bad hands, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, blurry, "
    if user_select_model == 4:
        base_prompt += "pixel, "
    
    image_urls = []

    for i in range(img_num):
        try:
            print(i+1, "번째 실행중")
            result = pipeline(
                prompt=base_prompt + prompt,
                negative_prompt=base_negative_prompt + negative_prompt,
                guidance_scale=8.0,
                num_inference_steps=30,
                num_images_per_prompt=1,
            )
            print(i+1, "번째 이미지 생성")
            image = result.images[0]
            image_path = f"{username}_{i + 1}.png"
            image.save(image_path)
            print(f"Image {i + 1} saved locally as {image_path}.")
            s3_key = f"generated_images/{image_path}"
            image_url = upload_to_s3(image_path, bucket_name, s3_key)
            image_urls.append(image_url)
        except Exception as e:
            print(f"Error during image generation or upload for image {i + 1}: {e}")

    return image_urls

if __name__ == "__main__":
    positive_prompt = sys.argv[1]
    negative_prompt = sys.argv[2]
    user_select_model = int(sys.argv[3])
    bucket_name = sys.argv[4]
    username = sys.argv[5]  # 추가된 부분
    
    img_num = 4
    model_id, vae = model_select(user_select_model)
    device = "cpu"

    try:
        pipeline = model_load(user_select_model, model_id, vae)
        pipeline.to(device)
        lora_select(pipeline, user_select_model)
        print(f"Model '{model_id}' loaded and moved to {device} successfully.")
        print(os.environ.get('AWS_ACCESS_KEY_ID'))
    except Exception as e:
        print(f"Error loading model or sending to device: {e}")
        raise
    print("이미지 생성 준비")
    image_urls = gen_img(positive_prompt, negative_prompt, img_num, pipeline, user_select_model, bucket_name, username)
    print("이미지 생성 끝")

    for url in image_urls:
        print(f"Generated image URL: {url}")
