<template>
  <div>
    {{ chartData }}
    <div>
      <b-row>
        <b-col class="my-2">
          <b-card header="D3">
            <GChart
              type="BubbleChart"
              :data="chartData"
              :options="chartOptions"
              style="width: 900px; height: 500px;"
            />
          </b-card>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      chartData: [
        ['ID', 'X', 'Y', 'Temperature'],
        ['', 80, 167, 120],
        ['', 79, 136, 130],
        ['', 78, 184, 50],
        ['', 72, 278, 230],
        ['', 81, 200, 210],
        ['', 72, 170, 100],
        ['', 68, 477, 80],
      ],
      chartOptions: {
        colorAxis: { colors: ['yellow', 'red'] },
      },
    };
  },
};
</script>
