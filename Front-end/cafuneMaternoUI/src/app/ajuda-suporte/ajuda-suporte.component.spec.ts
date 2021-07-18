import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjudaSuporteComponent } from './ajuda-suporte.component';

describe('AjudaSuporteComponent', () => {
  let component: AjudaSuporteComponent;
  let fixture: ComponentFixture<AjudaSuporteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjudaSuporteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AjudaSuporteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
