terraform {
  backend "s3" {
    region         = "ap-northeast-2"
    bucket         = "eks-terraform-workshop-ndgndg91"
    key            = "rds/terraform.tfstate"
    encrypt        = true
  }
}

provider "aws" {
  region = var.region
}

resource "aws_db_instance" "seller_auth" {
  allocated_storage    = 10
  engine               = "mysql"
  engine_version       = "5.7"
  instance_class       = "db.t3.micro"
  name                 = "seller_auth"
  username             = jsondecode(data.aws_secretsmanager_secret_version.seller_auth.secret_string).username
  password             = jsondecode(data.aws_secretsmanager_secret_version.seller_auth.secret_string).password
  parameter_group_name = "default.mysql5.7"
  skip_final_snapshot  = true
}