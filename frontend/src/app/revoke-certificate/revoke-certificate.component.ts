import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-revoke-certificate',
  templateUrl: './revoke-certificate.component.html',
  styleUrls: ['./revoke-certificate.component.css']
})
export class RevokeCertificateComponent implements OnInit {
  constructor(private router: Router, private _certificateService: CertificateService) { }

  ngOnInit(): void {
    if(history.state.data.type === 'ROOT'){
      this.RevokeDTO.issuerSerial = history.state.data.serialNumber
      this.RevokeDTO.certificateSerial = history.state.data.serialNumber
    }else{
      this.RevokeDTO.issuerSerial = history.state.data.issuer.serialNumber
      this.RevokeDTO.certificateSerial = history.state.data.serialNumber
    }

  }

  log() {
    //console.log(this.RevokeDTO)
    this.revokeCertificate()
  }

  revokeCertificate() {
    this._certificateService.revokeCertificate2(this.RevokeDTO).subscribe(data => this.router.navigate(["/home"]),
      error => alert(error.error));
  }

  RevokeDTO = {
    certificateSerial:"",
    issuerKeystorePass:"",
    issuerPrivateKeyPass : "",
    issuerSerial : "",
  }
}
