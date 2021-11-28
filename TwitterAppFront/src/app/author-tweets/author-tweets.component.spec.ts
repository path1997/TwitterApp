import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorTweetsComponent } from './author-tweets.component';

describe('AuthorTweetsComponent', () => {
  let component: AuthorTweetsComponent;
  let fixture: ComponentFixture<AuthorTweetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorTweetsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
