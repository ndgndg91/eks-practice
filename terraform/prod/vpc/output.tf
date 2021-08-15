output "application_vpc_id" {
  value = aws_vpc.application_vpc.id
}

output "application_vpc_cidr_blocks" {
  value = aws_vpc.application_vpc.cidr_block
}

output "rds_vpc_id" {
  value = aws_vpc.rds_vpc.id
}

output "rds_vpc_cidr_blocks" {
  value = aws_vpc.rds_vpc.cidr_block
}

output "application_public_1_subnet_id" {
  value = aws_subnet.application_public_1_subnet.id
}

output "application_public_2_subnet_id" {
  value = aws_subnet.application_public_2_subnet.id
}

output "application_public_3_subnet_id" {
  value = aws_subnet.application_public_3_subnet.id
}

output "application_public_1_subnet_cidr_blocks" {
  value = aws_subnet.application_public_1_subnet.cidr_block
}

output "application_public_2_subnet_cidr_blocks" {
  value = aws_subnet.application_public_2_subnet.cidr_block
}

output "application_public_3_subnet_cidr_blocks" {
  value = aws_subnet.application_public_3_subnet.cidr_block
}

output "application_private_1_subnet_id" {
  value = aws_subnet.application_private_1_subnet.id
}

output "application_private_2_subnet_id" {
  value = aws_subnet.application_private_2_subnet.id
}

output "application_private_3_subnet_id" {
  value = aws_subnet.application_private_3_subnet.id
}

output "application_private_1_subnet_cidr_blocks" {
  value = aws_subnet.application_private_1_subnet.cidr_block
}

output "application_private_2_subnet_cidr_blocks" {
  value = aws_subnet.application_private_2_subnet.cidr_block
}

output "application_private_3_subnet_cidr_blocks" {
  value = aws_subnet.application_private_3_subnet.cidr_block
}

output "rds_private_1_subnet_id" {
  value = aws_subnet.rds_private_1_subnet.id
}

output "rds_private_2_subnet_id" {
  value = aws_subnet.rds_private_2_subnet.id
}

output "rds_private_3_subnet_id" {
  value = aws_subnet.rds_private_3_subnet.id
}

output "rds_private_1_subnet_cidr_blocks" {
  value = aws_subnet.rds_private_1_subnet.cidr_block
}

output "rds_private_2_subnet_cidr_blocks" {
  value = aws_subnet.rds_private_2_subnet.cidr_block
}

output "rds_private_3_subnet_cidr_blocks" {
  value = aws_subnet.rds_private_3_subnet.cidr_block
}

output "rds_private_1_subnet_availability_zone" {
  value = aws_subnet.rds_private_1_subnet.availability_zone
}

output "rds_private_2_subnet_availability_zone" {
  value = aws_subnet.rds_private_2_subnet.availability_zone
}

output "rds_private_3_subnet_availability_zone" {
  value = aws_subnet.rds_private_3_subnet.availability_zone
}

output "nat_gateway_ip" {
  value = aws_eip.application_nat_eip.public_ip
}