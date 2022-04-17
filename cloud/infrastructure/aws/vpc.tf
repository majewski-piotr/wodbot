resource "aws_vpc" "main" {
  cidr_block           = "10.0.0.0/16"
  instance_tenancy     = "default"
  enable_dns_support   = "true"
  enable_dns_hostnames = "true"
  enable_classiclink   = "false"
  tags = {
    Name = "main"
  }
}

resource "aws_vpc_endpoint" "secret_manager_endpoint" {
  vpc_id       = aws_vpc.main.id
  service_name = "com.amazonaws.eu-central-1.secretsmanager"
  security_group_ids = [aws_security_group.main.id]
  vpc_endpoint_type = "Interface"
  subnet_ids = [aws_subnet.main-private-1.id]
  private_dns_enabled = true
}

resource "aws_subnet" "main-public-1" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.0.0/24"
  map_public_ip_on_launch = "true"
  availability_zone       = "eu-central-1a"

  tags = {
    Name = "main-public-1"
  }
}

resource "aws_subnet" "main-private-1" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = "false"
  availability_zone       = "eu-central-1a"

  tags = {
    Name = "main-private-1"
  }
}