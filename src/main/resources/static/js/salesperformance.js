fetch('/db/top-sales')
    .then(response => response.json())
    .then(data => {
        const labels = data.map(item => item.employeeName || "Unknown");
        const sales = data.map(item => item.totalSales / 1000 || 0);
        const contractCounts = data.map(item => item.contractCount || 0);

        const canvas = document.getElementById('salesChart');
        if (!canvas) {
            console.error('Canvas element not found');
            return;
        }

        const ctx = canvas.getContext('2d'); // ★★★ 필수 ★★★

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: '금액 (단위: 천원)',
                        data: sales,
                        backgroundColor: 'rgba(54, 162, 235, 0.7)',
                        borderWidth: 0,
                        yAxisID: 'y-sales'
                    },
                    {
                        label: '계약 건수',
                        data: contractCounts,
                        backgroundColor: 'rgba(255, 99, 132, 0.7)',
                        borderWidth: 0,
                        yAxisID: 'y-contracts'
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false, // ★ 필요하면 추가: 비율 깨지지 않게
                scales: {
                    'y-sales': {
                        type: 'linear',
                        position: 'left',
                        grid: { color: 'rgba(0,0,0,0)' },
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
                        grid: { color: 'rgba(0,0,0,0)' },
                        title: {
                            display: true,
                            text: '계약 건수'
                        }
                    },
                    x: {
                        grid: { color: 'rgba(0,0,0,0)' }
                    }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
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
