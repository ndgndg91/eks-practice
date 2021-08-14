terraform {
  backend "s3" {
    region         = "ap-northeast-2"
    bucket         = "eks-terraform-workshop-ndgndg91"
    key            = "vpc/terraform.tfstate"
    encrypt        = true
  }
}

provider "aws" {
  region = var.region
}