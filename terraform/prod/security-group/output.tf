output "rds_private_security_group_id" {
    value = aws_security_group.rds_private_subnet_security_group.id
}

output "rds_private_security_group_name" {
    value = aws_security_group.rds_private_subnet_security_group.name
}

output "application_public_subnet_security_group_id" {
    value = aws_security_group.application_public_subnet_security_group.id
}

output "application_public_subnet_security_group_name" {
    value = aws_security_group.application_public_subnet_security_group.name
}

output "bastion_host_security_group_id" {
    value = aws_security_group.bastion_host_security_group.id
}

output "bastion_host_security_group_name" {
    value = aws_security_group.bastion_host_security_group.name
}