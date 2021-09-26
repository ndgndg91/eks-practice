terraform {
  backend "s3" {
    region  = "ap-northeast-2"
    bucket  = "donggil-terraform-state"
    key     = "secrets-manager/terraform.tfstate"
    encrypt = true
  }
}

provider "aws" {
  region = var.region
}

locals {
  secret_name_auth = "eksworkshop/prod/auth/rds"
}


resource "aws_secretsmanager_secret" "prod_auth" {
  name = local.secret_name_auth

  tags = {
    Owned = "donggil"
  }
}

resource "random_password" "prod_auth" {
  length  = 16
  special = false
}

resource "aws_secretsmanager_secret_version" "prod_auth" {
  secret_id     = aws_secretsmanager_secret.prod_auth.id
  secret_string = <<EOF
   {
    "username": "admin",
    "password": "${random_password.prod_auth.result}"
   }
EOF
}