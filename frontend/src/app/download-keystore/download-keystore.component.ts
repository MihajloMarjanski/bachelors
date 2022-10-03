import { Component, OnInit , Input} from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-download-keystore',
  templateUrl: './download-keystore.component.html',
  styleUrls: ['./download-keystore.component.css']
})
export class DownloadKeystoreComponent implements OnInit {
  constructor(private router: Router, private _certificateService: CertificateService) { }

  ngOnInit(): void {
    this.KeystoreDTO.serial = history.state.data.serialNumber
  }
  log() {
    this.download()
  }

  download(){
    this._certificateService.getJKS2(this.KeystoreDTO).subscribe(response =>{
      //let fileName = response.headers.get('content-disposition')?.split(';')[1].split('=')[1];
      let fileName = 'keystore.zip'
      let blob = new Blob([response], {
        type: 'application/zip'
      });
      let a = document.createElement('a');
      a.download = fileName;
      a.href = window.URL.createObjectURL(blob);
      a.click();
    });
  }

  KeystoreDTO = {
    keystorePass:"",
    privateKeyPass : "",
    serial : "",
  }
}
