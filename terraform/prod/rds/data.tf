data terraform_remote_state "vpc" {
  backend = "s3"
  config = {
    bucket = var.remote_state_bucket
    key    = var.remote_vpc_state_key
    region = var.region
  }
}

data "terraform_remote_state" "security_group" {
  backend = "s3"
  config = {
    bucket = var.remote_state_bucket
    key    = var.remote_security_group_key
    region = var.region
  }
}

// 해당 버전은 console 을 통해서 생성했습니다.
data "aws_secretsmanager_secret_version" "prod_seller_auth" {
  secret_id = "prod/seller-auth/mysql"
}

// 해당 버전은 console 을 통해서 생성했습니다.
data "aws_secretsmanager_secret_version" "prod_buyer_auth" {
  secret_id = "prod/buyer-auth/mysql"
}