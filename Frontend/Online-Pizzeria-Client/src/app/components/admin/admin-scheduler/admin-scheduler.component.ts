import { Component, OnInit } from '@angular/core';
import { Scheduler } from 'src/app/models/Scheduler';
import { SchedulingService } from 'src/app/services/scheduling.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-admin-scheduler',
  templateUrl: './admin-scheduler.component.html',
  styleUrls: ['./admin-scheduler.component.css']
})
export class AdminSchedulerComponent implements OnInit {
  schedulers: Scheduler[];
  current: number;
  selected: number;
  constructor(
    private schedulingService: SchedulingService,
    private flashMessage: FlashMessagesService
    ) { }

  ngOnInit() {
    this.schedulingService.getSchedulingAlgorithms().subscribe(schedulers => {
      this.schedulers = schedulers;
      this.schedulers.forEach(scheduler => {
        if (scheduler.is_active) {
          this.current = scheduler.id;
          this.selected = scheduler.id;
        }
      })
    });
  }

  onSelect(id: number) {
    this.selected = id;
  }

  onSubmit() {
    this.schedulingService.postActiveAlgorithm(this.selected).subscribe(() => {
      this.flashMessage.show('Scheduler changed', { cssClass: 'alert-success', timeout: 4000 });
      this.current = this.selected;
    },
      err => {
        this.flashMessage.show(err.error.error, { cssClass: 'alert-danger', timeout: 4000 });
      }
    );
  }

}
