import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  getAuthoHeader() : any {
    const headers = {
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("token")
    }
    return{
      headers: headers
    };
  } 
  getDownalodOptions() : any {
    const headers = {
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("token")
    }
    const requestOptions: Object = {
      headers: headers,
      responseType: 'arraybuffer'
    };
    return requestOptions
  } 


  constructor(private http: HttpClient) { }

  login(request: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/authenticate", JSON.stringify(request), this.getAuthoHeader() );
  }

  createSubCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/createSub", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  createRootCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/createRoot", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  getAll(): Observable<any> {
    return this.http.get<any>("http://localhost:8080/api/certificates/getAll", this.getAuthoHeader() );
  }

  getJKS(alias : String): Observable<any> {
    return this.http.get<any>("http://localhost:8090/api/certificates/jks/"+alias,this.getDownalodOptions());
  }

  revokeCertificate(serial: any): Observable<any> {
    return this.http.get<any>("http://localhost:8080/api/certificates/revokeCert/"+serial,this.getAuthoHeader());
  }

  revokeCertificate2(revokeDTO: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/revokeCert/", JSON.stringify(revokeDTO),this.getAuthoHeader());
  }
  getJKS2(keyStoreDTO: any): Observable<any> {
    return this.http.post<any>("http://localhost:8090/api/certificates/jks", JSON.stringify(keyStoreDTO),this.getDownalodOptions());
  }
}
