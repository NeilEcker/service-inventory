<template>
  <div>
    <div>
      <b-row>
        <b-col class="my-2">
          <b-card header="D3">
            <GChart
              :settings="{ packages: ['orgchart'] }"
              type="OrgChart"
              :data="chartData"
              :options="chartOptions"
            />
          </b-card>
        </b-col>
      </b-row>
    </div>
    {{ chartData }}
  </div>
</template>

<script>
export default {
  data() {
    return {
      chartData: [
        ['Name', 'Manager', 'Tooltip'],
        [{v: 'Mike', f: 'Mike<div style="color:red; font-style:italic">President</div>'}, '', 'The President'],
        [{v: 'Jim', f: 'Jim<div style="color:red; font-style:italic">Vice President</div>'}, 'Mike', 'VP'],
        ['Alice', 'Mike', ''],
        ['Bob', 'Jim', 'Bob Sponge'],
        ['Carol', 'Bob', ''],
      ],
      chartOptions: {
        allowHtml: true,
      },
    };
  },
};
</script>
