output "rds_private_security_group_id" {
    value = aws_security_group.rds_private_subnet_security_group.id
}

output "rds_private_security_group_name" {
    value = aws_security_group.rds_private_subnet_security_group.name
}