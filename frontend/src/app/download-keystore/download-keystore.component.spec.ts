import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadKeystoreComponent } from './download-keystore.component';

describe('DownloadKeystoreComponent', () => {
  let component: DownloadKeystoreComponent;
  let fixture: ComponentFixture<DownloadKeystoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DownloadKeystoreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DownloadKeystoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
