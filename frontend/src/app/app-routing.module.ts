import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { CreateRootComponent } from './create-root/create-root.component';
import { DownloadKeystoreComponent } from './download-keystore/download-keystore.component';
import { ListCertificatesComponent } from './list-certificates/list-certificates.component';
import { LoginComponent } from './login/login.component';
import { RevokeCertificateComponent } from './revoke-certificate/revoke-certificate.component';

const routes: Routes = [
  {path:'home', component: ListCertificatesComponent},
  {path:'create-certificate', component: AddCertificateComponent},
  {path:'create-root', component: CreateRootComponent},
  {path:'login', component: LoginComponent},
  {path:'revoke', component: RevokeCertificateComponent},
  {path:'download', component: DownloadKeystoreComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
