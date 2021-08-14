variable "region" {
  description = "AWS Region"
}

variable "application_vpc_cidr" {
  description = "Application Vpc Cidr"
}

variable "rds_vpc_cidr" {
  description = "RDS Vpc Cidr"
}

variable "application_public_1_subnet_cidr" {
  description = "Application Public Subnet 1"
}

variable "application_public_2_subnet_cidr" {
  description = "Application Public Subnet 2"
}

variable "application_public_3_subnet_cidr" {
  description = "Application Public Subnet 3"
}

variable "application_private_1_subnet_cidr" {
  description = "Application Private Subnet 1"
}

variable "application_private_2_subnet_cidr" {
  description = "Application Private Subnet 2"
}

variable "application_private_3_subnet_cidr" {
  description = "Application Private Subnet 3"
}

variable "rds_private_1_subnet_cidr" {
  description = "RDS Private Subnet 1"
}

variable "rds_private_2_subnet_cidr" {
  description = "RDS Private Subnet 2"
}

variable "rds_private_3_subnet_cidr" {
  description = "RDS Private Subnet 3"
}

variable "availability_zone_1" {
  description = "first az"
}

variable "availability_zone_2" {
  description = "second az"
}

variable "availability_zone_3" {
  description = "third az"
}