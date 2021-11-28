import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteAuthorTweetsComponent } from './favourite-author-tweets.component';

describe('FavouriteAuthorTweetsComponent', () => {
  let component: FavouriteAuthorTweetsComponent;
  let fixture: ComponentFixture<FavouriteAuthorTweetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouriteAuthorTweetsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouriteAuthorTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
