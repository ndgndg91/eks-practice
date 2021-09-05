terraform {
  backend "s3" {
    region         = "ap-northeast-2"
    bucket         = "eks-terraform-workshop-ndgndg91"
    key            = "security-group/terraform.tfstate"
    encrypt        = true
  }
}

provider "aws" {
  region = var.region
}

locals {
  all_addresses_cidr_block = ["0.0.0.0/0"]

  tcp_protocol = "TCP"
  udp_protocol = "UDP"
  all_protocol = "-1"

  udp_vpn_port = 3000
  http_port = 80
  https_port = 443
  ssh_port = 22
  mysql_port = 3306
  all_port = 0
}

resource "aws_security_group" "bastion_host_security_group" {
  name = "Bastion SSM Security Group"
  description = "Bastion Host Security Group"
  vpc_id = data.terraform_remote_state.vpc.outputs.application_vpc_id
  
  ingress {
    from_port = local.http_port
    protocol =  local.tcp_protocol
    to_port = local.http_port
    cidr_blocks = local.all_addresses_cidr_block
  }
  
  ingress {
    from_port = local.ssh_port
    protocol =  local.tcp_protocol
    to_port = local.ssh_port
    cidr_blocks = local.all_addresses_cidr_block
  }
  
  ingress {
    from_port = local.https_port
    protocol = local.tcp_protocol
    to_port = local.https_port
    cidr_blocks = local.all_addresses_cidr_block
  }
  
  egress {
    from_port = local.all_port
    protocol = local.all_protocol
    to_port = local.all_port
    cidr_blocks = local.all_addresses_cidr_block
  }
}

resource "aws_security_group" "application_public_subnet_security_group" {
  name = "Public ALB Security Group"
  description = "ALB Security Group Allow 80 to 80"
  vpc_id = data.terraform_remote_state.vpc.outputs.application_vpc_id

  ingress {
    from_port = local.http_port
    protocol = local.tcp_protocol
    to_port = local.http_port
    cidr_blocks = local.all_addresses_cidr_block
  }

  ingress {
    from_port = local.ssh_port
    protocol = local.tcp_protocol
    to_port = local.ssh_port
    cidr_blocks = [data.terraform_remote_state.vpc.outputs.application_vpc_cidr_blocks]
  }

  egress {
    from_port = local.all_port
    protocol = local.all_protocol
    to_port = local.all_port
    cidr_blocks = local.all_addresses_cidr_block
  }

  tags = {
    Name = "Public ALB Security Group"
  }
}

resource "aws_security_group" "application_private_subnet_security_group" {
  name = "Private Instance Security Group"
  description = "Instance In Private Subnet Security Group"
  vpc_id = data.terraform_remote_state.vpc.outputs.application_vpc_id

  ingress {
    from_port = local.all_port
    protocol = local.all_protocol
    to_port = local.all_port
    cidr_blocks = [
      data.terraform_remote_state.vpc.outputs.application_public_1_subnet_cidr_blocks,
      data.terraform_remote_state.vpc.outputs.application_public_2_subnet_cidr_blocks,
      data.terraform_remote_state.vpc.outputs.application_private_1_subnet_cidr_blocks,
      data.terraform_remote_state.vpc.outputs.application_private_2_subnet_cidr_blocks,
    ]
  }

  egress {
    from_port = local.all_port
    protocol = local.all_protocol
    to_port = local.all_port
    cidr_blocks = local.all_addresses_cidr_block
  }

  tags = {
    Name = "Private Instance Security Group"
  }
}

resource "aws_security_group" "rds_private_subnet_security_group" {
  name = "RDS Security Group"
  description = "RDS Security Group Allow 3306"
  vpc_id = data.terraform_remote_state.vpc.outputs.rds_vpc_id

  ingress {
    from_port = local.mysql_port
    protocol = local.tcp_protocol
    to_port = local.mysql_port
    cidr_blocks = [
      data.terraform_remote_state.vpc.outputs.application_public_1_subnet_cidr_blocks,
      data.terraform_remote_state.vpc.outputs.application_public_2_subnet_cidr_blocks,
      data.terraform_remote_state.vpc.outputs.application_private_1_subnet_cidr_blocks,
      data.terraform_remote_state.vpc.outputs.application_private_2_subnet_cidr_blocks,
    ]
  }

  egress {
    from_port = local.all_port
    protocol = local.all_protocol
    to_port = local.all_port
    cidr_blocks = local.all_addresses_cidr_block
  }

  tags = {
    Name = "RDS Security Group"
  }
}