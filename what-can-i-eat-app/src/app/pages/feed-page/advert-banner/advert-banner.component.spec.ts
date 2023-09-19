import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdvertBannerComponent } from './advert-banner.component';


describe('AdvertBannerComponent', () => {
  let component: AdvertBannerComponent;
  let fixture: ComponentFixture<AdvertBannerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvertBannerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdvertBannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
