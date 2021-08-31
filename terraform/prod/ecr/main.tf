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
    eks_workshop_seller_auth="eks-workshop-seller-auth"
    eks_workshop_buyer_auth="eks-workshop-buyer-auth-"
    eks_workshop_product="eks-workshop-product"
    eks_workshop_order="eks-workshop-order"
    eks_workshop_batch="eks-workshop-batch"
}

resource "aws_ecr_repository" "eks_workshop_seller_auth" {
  name                 = local.eks_workshop_seller_auth
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "eks_workshop_buyer_auth" {
  name                 = local.eks_workshop_buyer_auth
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "eks_workshop_product" {
  name                 = local.eks_workshop_product
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "eks_workshop_order" {
  name                 = local.eks_workshop_order
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