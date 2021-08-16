terraform {
  backend "s3" {
    region         = "ap-northeast-2"
    bucket         = "eks-terraform-workshop-ndgndg91"
    key            = "s3/terraform.tfstate"
    encrypt        = true
  }
}
provider "aws" {
  region = var.region
}

resource "aws_s3_bucket" "eks_practice_batch_object" {
  bucket = "eks-practice-batch-object"
}