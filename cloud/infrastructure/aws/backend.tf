terraform {
  backend "s3" {
    bucket                  = "terraform-state-1a4f"
    key                     = "global/s3/wodbot/terraform.tfstate"
    region                  = "eu-central-1"
    shared_credentials_file = "credentials"
    profile                 = "default"
  }
}