terraform {
  backend "s3" {
    region         = "ap-northeast-2"
    bucket         = "eks-terraform-workshop-ndgndg91"
    key            = "ecr/terraform.tfstate"
    encrypt        = true
  }
}

provider "aws" {
  region = var.region
}

locals {
    eks_workshop_api="eks-workshop-api"
    eks_workshop_front="eks-workshop-front"
    eks_workshop_batch="eks-workshop-batch"
}

resource "aws_ecr_repository" "eks_workshop_api" {
  name                 = local.eks_workshop_api
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "eks_workshop_front" {
  name                 = local.eks_workshop_front
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "eks_workshop_batch" {
  name                 = local.eks_workshop_batch
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}