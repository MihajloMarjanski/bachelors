import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-create-root',
  templateUrl: './create-root.component.html',
  styleUrls: ['./create-root.component.css']
})
export class CreateRootComponent implements OnInit {

  constructor(private router: Router, private _certificateService: CertificateService) { }

  ngOnInit(): void {
  }

  log() {
    //console.log(this.CertDTO)
    this.createRootCertificate()
  }

  CertDTO = {
    serial:22,
    alias: "",
    keystorePass:"",
    privateKeyPass : "",
    validFrom:null,
    validUntil:null,
    commonName:"",
    organisationUnit:"",
    organisationName:"",
    email:"",

    digitalSignature:false,
    nonRepudiation:false,
    keyEncipherment:false,
    dataEncipherment:false,
    keyAgreement:false,
    keyCertSign:false,
    cRLSign:false,
    encipherOnly:false,
    decipherOnly:false,

  }

  createRootCertificate() {
    this._certificateService.createRootCertificate(this.CertDTO).subscribe(data => this.router.navigateByUrl("/home"),
      error => alert(error.error));
  }
}
