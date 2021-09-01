terraform {
  backend "s3" {
    region         = "ap-northeast-2"
    bucket         = "eks-terraform-workshop-ndgndg91"
    key            = "secrets-manager/terraform.tfstate"
    encrypt        = true
  }
}

provider "aws" {
  region = var.region
}

locals {
  secret_name_seller_auth="prod/seller-auth/rds"
  secret_name_buyer_auth="prod/buyer-auth/rds"
}


resource "aws_secretsmanager_secret" "prod_seller_auth" {
  name = local.secret_name_seller_auth
}

resource "aws_secretsmanager_secret" "prod_buyer_auth" {
  name = local.secret_name_buyer_auth
}

resource "random_password" "prod_seller_auth" {
  length           = 16
  special          = false
}

resource "random_password" "prod_buyer_auth" {
  length            = 16
  special           = false
}

resource "aws_secretsmanager_secret_version" "prod_seller_auth" {
  secret_id     = aws_secretsmanager_secret.prod_seller_auth.id
  secret_string = <<EOF
   {
    "username": "admin",
    "password": "${random_password.prod_seller_auth.result}"
   }
EOF
}

resource "aws_secretsmanager_secret_version" "prod_buyer_auth" {
  secret_id     = aws_secretsmanager_secret.prod_buyer_auth.id
  secret_string = <<EOF
   {
    "username": "admin",
    "password": "${random_password.prod_buyer_auth.result}"
   }
EOF
}