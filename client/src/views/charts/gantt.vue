<template>
  <div>
    <div v-if="chartData.length > 1">
      <GChart
        :settings="{ packages: ['corechart', 'table', 'gantt'] }"
        type="Gantt"
        :data="chartData"
        :options="chartOptions"
        ref="gChart"
      />
    </div>
    <div v-else>
      No open projects with dates specified.
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ProjectApiService from '@/services/project.api';
import moment from 'moment';

@Component
export default class Gantt extends Vue {
  private chartData: any = [
        ['Task ID', 'Task Name', 'Type', 'Start Date', 'End Date', 'Duration', 'Percent Complete', 'Dependencies'],
        /*['Task 1', 'GIN 2.0', 'Application', new Date(2018, 11, 5), new Date(2019, 0, 14), 1,  50,  null],
        ['Task 2', 'Rebuild McDonald', 'Infrastructure', null, new Date(2019, 0, 12), 1, 25, null],
        ['Task 3', 'Do Something Else', 'Application', new Date(2019, 1, 12), new Date(2019, 2, 12), 1, 50, null],*/
  ];
  private chartOptions: any = {
    height: 1200,
    gantt: {
      labelStyle: {
        fontName: ['RobotoCondensedRegular'],
        fontSize: 14,
        color: '#757575',
      },
    },
  };
  private showCompleted: string = 'false';

  private async mounted() {
    this.getChartData();
  }

  private getChartData() {
    ProjectApiService.gantt().then( (response) => {
        for (const project of response.data) {
          const startDate: Date | null = project.start ? new Date(moment(project.start).toDate()) : null;
          const endDate: Date | null = project.end ? new Date(moment(project.end).toDate()) : null;
          const show: boolean = project.complete < 100;
          if (startDate && endDate && show) {
            this.chartData.push([project.name, project.name, project.type,
              new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()),
              new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate()),
              1, project.complete, null]);
          }
        }
      });
  }
}
</script>
