import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrashTweetsComponent } from './trash-tweets.component';

describe('TrashTweetsComponent', () => {
  let component: TrashTweetsComponent;
  let fixture: ComponentFixture<TrashTweetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrashTweetsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrashTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
