terraform {
  backend "s3" {
    bucket                  = "terraform-state-1a4f"
    key                     = "global/s3/wodbot/terraform.tfstate"
    region                  = "eu-central-1"
    shared_credentials_file = "credentials"
    profile                 = "default"
  }
  required_version = ">= 0.12"
}

provider "aws" {
  region                   = var.AWS_REGION
  shared_credentials_files = ["credentials"]
  profile                  = "default"
}