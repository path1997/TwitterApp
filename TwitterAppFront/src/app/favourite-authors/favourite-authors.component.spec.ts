import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteAuthorsComponent } from './favourite-authors.component';

describe('FavouriteAuthorsComponent', () => {
  let component: FavouriteAuthorsComponent;
  let fixture: ComponentFixture<FavouriteAuthorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouriteAuthorsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouriteAuthorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
