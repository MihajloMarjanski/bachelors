import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCertificatesComponent } from './list-certificates.component';

describe('ListCertificatesComponent', () => {
  let component: ListCertificatesComponent;
  let fixture: ComponentFixture<ListCertificatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListCertificatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListCertificatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
