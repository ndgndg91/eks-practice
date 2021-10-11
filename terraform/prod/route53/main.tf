terraform {
  backend "s3" {
    region  = "ap-northeast-2"
    bucket  = "donggil-terraform-state"
    key     = "route53/terraform.tfstate"
    encrypt = true
  }
}

provider "aws" {
  region = var.region
}

locals {
  validation_method              = "DNS"
  dong_gil_com_alternative_names = ["*.dong-gil.com"]
}


resource "aws_route53_zone" "dong_gil_com" {
  name = "dong-gil.com"

  tags = {
    Owned = "donggil"
  }
}

resource "aws_acm_certificate" "dong_gil_com" {
  domain_name       = aws_route53_zone.dong_gil_com.name
  validation_method = local.validation_method

  subject_alternative_names = local.dong_gil_com_alternative_names

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_route53_record" "dong_gil_com" {
  for_each = {
    for dvo in aws_acm_certificate.dong_gil_com.domain_validation_options : dvo.domain_name => {
      name   = dvo.resource_record_name
      record = dvo.resource_record_value
      type   = dvo.resource_record_type
    }
  }

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = aws_route53_zone.dong_gil_com.zone_id
}