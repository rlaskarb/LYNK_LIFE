$(function() {
    $('#department-tree').jstree({
        'core': {
            'data': {
                'url': '/api/departments',
                'dataType': 'json',
                'data': function(node) {
                    return { 'id': node.id };
                }
            }
        },
        'plugins': ['checkbox']
    }).on('select_node.jstree', function(e, data) {
        if (data.node.children.length === 0) {
            $.getJSON('/api/departments/' + data.node.id + '/employees', function(employees) {
                $.each(employees, function(i, employee) {
                    $('#department-tree').jstree('create_node', data.node, {
                        'id': 'emp_' + employee.id,
                        'text': employee.name,
                        'icon': 'jstree-file'
                    });
                });
            });
        }
    });
});