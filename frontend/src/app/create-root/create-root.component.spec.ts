import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRootComponent } from './create-root.component';

describe('CreateRootComponent', () => {
  let component: CreateRootComponent;
  let fixture: ComponentFixture<CreateRootComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateRootComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRootComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
