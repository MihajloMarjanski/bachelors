import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  LoginDTO = {
    alias:"",
    privateKeyPassword:""
  }

  constructor(private router: Router, private _certificateService: CertificateService) { }

  ngOnInit(): void {
  }

  handleError(){
    this.LoginDTO.alias = ""
    this.LoginDTO.privateKeyPassword = ""
    alert("Failed to login please try again")
  }

  login() {
    this._certificateService.login(this.LoginDTO).subscribe(data => localStorage.setItem('token', data.jwt),
    error => console.log(error));
    /*this._registerService.login(this.LoginDTO).subscribe(
      data => localStorage.setItem('PSWtoken', data.jwt),
      error => this.handleError(),
      () => this.router.navigate(["/medical-record"]));*/
  }
  verifyPassword() {
    if(this.LoginDTO.privateKeyPassword == "")
      return false;
    return true;
  }

  verifyUsername() {
    if(this.LoginDTO.alias == "")
      return false;
    return true;
  }

  contentIsValid() {
    if(!this.verifyPassword())
      return false;
    if(!this.verifyUsername())
      return false;
    return true;
  }

}
