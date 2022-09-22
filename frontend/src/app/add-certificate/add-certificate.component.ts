import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {

  constructor(private router: Router, private _certificateService: CertificateService) { }

  ngOnInit(): void {
    this.CertDTO.serverAlternativeNames.length = 0
  }

  log() {
    //console.log(this.CertDTO)
    this.createSubCertificate()
  }

  addSan() {
    if (!this.CertDTO.serverAlternativeNames.includes(this.san)){
      this.CertDTO.serverAlternativeNames.push(this.san)
      this.san = ""
    }
  }
  san =""

  CertDTO = {
    alias: "",
    keystorePass:"",
    privateKeyPass : "",
    type:"EE",
    validFrom:null,
    validUntil:null,
    commonName:"",
    organisationUnit:"",
    organisationName:"",
    email:"",
    serverAlternativeNames: [""],

    digitalSignature:false,
    nonRepudiation:false,
    keyEncipherment:false,
    dataEncipherment:false,
    keyAgreement:false,
    keyCertSign:false,
    cRLSign:false,
    encipherOnly:false,
    decipherOnly:false,

    issuerKeystorePass:"",
    issuerPrivateKeyPass : "",
    issuerSerial : "",

  }


  createSubCertificate() {
    this._certificateService.createSubCertificate(this.CertDTO).subscribe(data => console.log(data),
      error => alert(error.error));
  }
}
