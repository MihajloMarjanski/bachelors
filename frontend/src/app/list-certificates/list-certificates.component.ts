import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-list-certificates',
  templateUrl: './list-certificates.component.html',
  styleUrls: ['./list-certificates.component.css']
})
export class ListCertificatesComponent implements OnInit {

  constructor(private router: Router, private _certificateService: CertificateService) { }

  certificates = [] as any;

  ngOnInit(): void {
    this.getAllCertificates()
  }


  getAllCertificates() {
    this._certificateService.getAll().subscribe(data => this.certificates = data);
  }


  revokeCertificate(certificate: any) {
    this.router.navigate(['/revoke'],{state: {data: certificate}})
  }
  download(certificate: any){
    this.router.navigate(['/download'],{state: {data: certificate}})
  }
}
