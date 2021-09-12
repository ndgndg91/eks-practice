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

locals {
  application_vpc_instance_tenancy = "default"
  rds_vpc_instance_tenancy = "default"
  nat_destination_cidr_block = "0.0.0.0/0"
}

// application node 를 provisioning 하기 위한 vpc
resource "aws_vpc" "application_vpc" {
  cidr_block           = var.application_vpc_cidr
  enable_dns_hostnames = false
  enable_dns_support   = true
  instance_tenancy     = local.application_vpc_instance_tenancy

  tags = {
    Name                                         = "application"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// rds 를  provisioning 하기 위한 vpc
resource "aws_vpc" "rds_vpc" {
  cidr_block           = var.rds_vpc_cidr
  enable_dns_hostnames = false
  enable_dns_support   = true
  instance_tenancy     = local.rds_vpc_instance_tenancy

  tags = {
    Name = "rds"
  }
}

// 가용 영역 a 의 web tier public subnet
resource "aws_subnet" "application_public_1_subnet" {
  vpc_id                  = aws_vpc.application_vpc.id
  cidr_block              = var.application_public_1_subnet_cidr
  availability_zone       = var.availability_zone_1
  map_public_ip_on_launch = false

  tags = {
    Name = "application_public_1"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// 가용 영역 b 의 web tier public subnet
resource "aws_subnet" "application_public_2_subnet" {
  vpc_id                  = aws_vpc.application_vpc.id
  cidr_block              = var.application_public_2_subnet_cidr
  availability_zone       = var.availability_zone_2
  map_public_ip_on_launch = false

  tags = {
    Name = "application_public_2"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// 가용 영역 c 의 web tier public subnet
resource "aws_subnet" "application_public_3_subnet" {
  vpc_id                  = aws_vpc.application_vpc.id
  cidr_block              = var.application_public_3_subnet_cidr
  availability_zone       = var.availability_zone_3
  map_public_ip_on_launch = false

  tags = {
    Name = "application_public_3"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// 가용 영역 a 의 application private subnet
resource "aws_subnet" "application_private_1_subnet" {
  vpc_id                  = aws_vpc.application_vpc.id
  cidr_block              = var.application_private_1_subnet_cidr
  availability_zone       = var.availability_zone_1
  map_public_ip_on_launch = false

  tags = {
    Name = "application_private_1"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// 가용 영역 b 의 application private subnet
resource "aws_subnet" "application_private_2_subnet" {
  vpc_id                  = aws_vpc.application_vpc.id
  cidr_block              = var.application_private_2_subnet_cidr
  availability_zone       = var.availability_zone_2
  map_public_ip_on_launch = false

  tags = {
    Name = "application_private_2"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// 가용 영역 c 의 application private subnet
resource "aws_subnet" "application_private_3_subnet" {
  vpc_id                  = aws_vpc.application_vpc.id
  cidr_block              = var.application_private_3_subnet_cidr
  availability_zone       = var.availability_zone_3
  map_public_ip_on_launch = false

  tags = {
    Name = "application_private_3"
    "kubernetes.io/cluster/eks-workshop-cluster" = "shared"
    "kubernetes.io/role/elb"                     = "1"
  }
}

// 가용 영역 a 의 rds private subnet
resource "aws_subnet" "rds_private_1_subnet" {
  vpc_id                  = aws_vpc.rds_vpc.id
  cidr_block              = var.rds_private_1_subnet_cidr
  availability_zone       = var.availability_zone_1
  map_public_ip_on_launch = false

  tags = {
    Name = "rds_private_1"
  }
}

// 가용 영역 b 의 rds private subnet
resource "aws_subnet" "rds_private_2_subnet" {
  vpc_id                  = aws_vpc.rds_vpc.id
  cidr_block              = var.rds_private_2_subnet_cidr
  availability_zone       = var.availability_zone_2
  map_public_ip_on_launch = false

  tags = {
    Name = "rds_private_2"
  }
}

// 가용 영역 c 의 rds private subnet
resource "aws_subnet" "rds_private_3_subnet" {
  vpc_id                  = aws_vpc.rds_vpc.id
  cidr_block              = var.rds_private_3_subnet_cidr
  availability_zone       = var.availability_zone_3
  map_public_ip_on_launch = false

  tags = {
    Name = "rds_private_3"
  }
}

// public subnets routing table
// internet gateway association
resource "aws_route_table" "application_public_route_table" {
  vpc_id = aws_vpc.application_vpc.id

  tags = {
    Name = "application_public__route_table"
  }
}

// private subnets routing table
// nat gateway association
resource "aws_route_table" "application_private_route_table" {
  vpc_id = aws_vpc.application_vpc.id

  tags = {
    Name = "application_private__route_table"
  }
}

// rds subnets routing table
resource "aws_route_table" "rds_private_route_table" {
  vpc_id = aws_vpc.rds_vpc.id

  tags = {
    Name = "rds_private__route_table"
  }
}

// internet gateway
resource "aws_internet_gateway" "application_vpc_internet_gateway" {
  vpc_id = aws_vpc.application_vpc.id

  tags = {
    Name = "application_vpc_internet_gateway"
  }
}

// internet gateway 를 public subnets routing table route 설정
resource "aws_route" "application_public_internet_gateway_route" {
  route_table_id         = aws_route_table.application_public_route_table.id
  gateway_id             = aws_internet_gateway.application_vpc_internet_gateway.id
  destination_cidr_block = "0.0.0.0/0"
}

// 가용 영역 a 의 web tier public subnet routing table association
resource "aws_route_table_association" "application_public_subnet_1_association" {
  route_table_id = aws_route_table.application_public_route_table.id
  subnet_id      = aws_subnet.application_public_1_subnet.id
}

// 가용 영역 b 의 web tier public subnet routing table association
resource "aws_route_table_association" "application_public_subnet_2_association" {
  route_table_id = aws_route_table.application_public_route_table.id
  subnet_id      = aws_subnet.application_public_2_subnet.id
}

// 가용 영역 c 의 web tier public subnet routing table association
resource "aws_route_table_association" "application_public_subnet_3_association" {
  route_table_id = aws_route_table.application_public_route_table.id
  subnet_id      = aws_subnet.application_public_3_subnet.id
}

// 가용 영역 a 의 application private subnet routing table association
resource "aws_route_table_association" "application_private_subnet_1_association" {
  route_table_id = aws_route_table.application_private_route_table.id
  subnet_id      = aws_subnet.application_private_1_subnet.id
}

// 가용 영역 b 의 application private subnet routing table association
resource "aws_route_table_association" "application_private_subnet_2_association" {
  route_table_id = aws_route_table.application_private_route_table.id
  subnet_id      = aws_subnet.application_private_2_subnet.id
}

// 가용 영역 c 의 application private subnet routing table association
resource "aws_route_table_association" "application_private_subnet_3_association" {
  route_table_id = aws_route_table.application_private_route_table.id
  subnet_id      = aws_subnet.application_private_3_subnet.id
}

// 가용 영역 a 의 rds private subnet routing table association
resource "aws_route_table_association" "rds_private_subnet_1_association" {
  route_table_id = aws_route_table.rds_private_route_table.id
  subnet_id      = aws_subnet.rds_private_1_subnet.id
}

// 가용 영역 b 의 rds private subnet routing table association
resource "aws_route_table_association" "rds_private_subnet_2_association" {
  route_table_id = aws_route_table.rds_private_route_table.id
  subnet_id      = aws_subnet.rds_private_2_subnet.id
}

// 가용 영역 c 의 rds private subnet routing table association
resource "aws_route_table_association" "rds_private_subnet_3_association" {
  route_table_id = aws_route_table.rds_private_route_table.id
  subnet_id      = aws_subnet.rds_private_3_subnet.id
}

// application vpc 와 rds vpc 의 peering
resource "aws_vpc_peering_connection" "application_vpc_peering_rds_vpc" {
  vpc_id      = aws_vpc.rds_vpc.id // requester
  peer_vpc_id = aws_vpc.application_vpc.id // accepter
  auto_accept = true

  tags = {
    Name = "vpc_peering_between_application_and_rds"
  }
}

// application public route table 애 rds vpc 라우팅 설정
resource "aws_route" "application_public_to_rds_private" {
  route_table_id = aws_route_table.application_public_route_table.id
  vpc_peering_connection_id = aws_vpc_peering_connection.application_vpc_peering_rds_vpc.id
  destination_cidr_block = aws_vpc.rds_vpc.cidr_block
}

// application private route table 에 rds vpc 라우팅 설정
resource "aws_route" "application_private_to_rds_private" {
  route_table_id            = aws_route_table.application_private_route_table.id
  vpc_peering_connection_id = aws_vpc_peering_connection.application_vpc_peering_rds_vpc.id
  destination_cidr_block    = aws_vpc.rds_vpc.cidr_block
}

// rds private route table 에 application vpc 라우팅 설정
resource "aws_route" "rds_private_to_application_private" {
  route_table_id            = aws_route_table.rds_private_route_table.id
  vpc_peering_connection_id = aws_vpc_peering_connection.application_vpc_peering_rds_vpc.id
  destination_cidr_block    = aws_vpc.application_vpc.cidr_block
}

// nat gw 에 붙일 eip
resource "aws_eip" "application_nat_eip" {
  vpc = true

  tags = {
    Name = "application_nat_gw_eip"
  }
}

// 가용 영역 a 에 provisioning 할 nat gw
resource "aws_nat_gateway" "application_nat_gw" {
  allocation_id = aws_eip.application_nat_eip.id
  subnet_id     = aws_subnet.application_public_1_subnet.id

  tags = {
    Name = "NAT_gateway_in_application_public_1_subnet"
  }
}

// nat gw routing table 설정
resource "aws_route" "application_nat_gw_in_public_1_subnet" {
  route_table_id         = aws_route_table.application_private_route_table.id
  nat_gateway_id         = aws_nat_gateway.application_nat_gw.id
  destination_cidr_block = local.nat_destination_cidr_block
}