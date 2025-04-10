fetch('/db/top-sales')
    .then(response => response.json())
    .then(data => {
        const labels = data.map(item => item.employeeName || "Unknown");
        const sales = data.map(item => item.totalSales / 1000 || 0);
        const contractCounts = data.map(item => (item.contractCount || 0));

        const ctx = document.getElementById('salesChart');
        if (!ctx) {
            console.error('Canvas element not found');
            return;
        }

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: '금액 (단위: 천원)',
                        data: sales,
                        backgroundColor: 'rgba(54, 162, 235, 0.7)', // 막대 색상 유지
                        borderColor: 'rgba(54, 162, 235, 0)', // 테두리 투명
                        borderWidth: 0, // 막대 테두리 제거
                        yAxisID: 'y-sales'
                    },
                    {
                        label: '계약 건수',
                        data: contractCounts,
                        backgroundColor: 'rgba(255, 99, 132, 0.7)', // 막대 색상 유지
                        borderColor: 'rgba(255, 99, 132, 0)', // 테두리 투명
                        borderWidth: 0, // 막대 테두리 제거
                        yAxisID: 'y-contracts'
                    }
                ]
            },
            options: {
                responsive: true,
                scales: {
                    'y-sales': {
                        type: 'linear',
                        position: 'left',
                        grid: {

                            color: 'rgba(1, 0, 0, 0)' // 격자선 투명 처리
                        },
                        ticks: {
                            display: true
                        },
                        title: {
                            display: true,
                            text: '금액 (단위: 천원)'
                        }
                    },
                    'y-contracts': {
                        type: 'linear',
                        position: 'right',
                        min: 0,
                        max: 24,
                        grid: {

                            color: 'rgba(1, 0, 0, 0)' // 격자선 투명 처리
                        },
                        ticks: {

                        },
                        title: {
                            display: true,
                            text: '계약 건수'
                        }
                    },
                    x: {
                        grid: {

                            color: 'rgba(1, 0, 0, 0)' // x축 격자선 투명 처리
                        },
                        ticks: {

                        }
                    }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        callbacks: {
                            label: function (tooltipItem) {
                                if (tooltipItem.datasetIndex === 0) {
                                    return tooltipItem.raw + '천원';
                                } else {
                                    return tooltipItem.raw + '건';
                                }
                            }
                        }
                    }
                }
            }
        });
    })
    .catch(error => console.error('Error fetching data:', error));
