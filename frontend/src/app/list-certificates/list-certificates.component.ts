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

  getSubordinateCertificates() {
    /*this._certificateService.getSubordinateCertificates(this.currentUser).subscribe(data => this.allCertificates = data); */
  }

  revokeCertificate(serialNumber: number) {
    this._certificateService.revokeCertificate(serialNumber).subscribe(data => console.log("Revoked"),
      error => console.log(error));
  }

  logout(){
    /*localStorage.clear();*/
  }

  checkValidity(serialNumber: number){
    /*this._certificateService.checkValidity(serialNumber).subscribe(data => alert(data),
      error => console.log(error));*/
  }

  download(alias : string){
    this._certificateService.getJKS(alias).subscribe(response =>{
      //let fileName = response.headers.get('content-disposition')?.split(';')[1].split('=')[1];
      let fileName = alias+'.zip'
      let blob = new Blob([response], {
        type: 'application/zip'
      });
      let a = document.createElement('a');
      a.download = fileName;
      a.href = window.URL.createObjectURL(blob);
      a.click();
    });
  }
}
