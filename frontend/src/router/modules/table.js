/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const tableRouter = {
  path: '/table',
  component: Layout,
  redirect: '/table/complex-table',
  name: 'Table',
  meta: {
    title: '设备管理',
    icon: 'table'
  },
  children: [
    // {
    //   path: 'dynamic-table',
    //   component: () => import('@/views/table/dynamic-table/index'),
    //   name: 'DynamicTable',
    //   meta: { title: 'dynamicTable' }
    // },
    // {
    //   path: 'drag-table',
    //   component: () => import('@/views/table/drag-table'),
    //   name: 'DragTable',
    //   meta: { title: 'dragTable' }
    // },
    // {
    //   path: 'inline-edit-table',
    //   component: () => import('@/views/table/inline-edit-table'),
    //   name: 'InlineEditTable',
    //   meta: { title: 'inlineEditTable' }
    // },
    // {
    //   path: 'complex-table',
    //   component: () => import('@/views/table/complex-table'),
    //   name: 'ComplexTable',
    //   meta: { title: 'complexTable' }
    // },
    {
      path: 'project-table',
      component: () => import('@/views/table/project-table'),
      name: 'ProjectTable',
      meta: { title: '项目管理' }
    },
    {
      path: 'employee-table',
      component: () => import('@/views/table/employee-table'),
      name: 'EmployeeTable',
      meta: { title: '雇员管理' }
    },
    {
      path: 'department-table',
      component: () => import('@/views/table/department-table'),
      name: 'DepartmentTable',
      meta: { title: '部门管理' }
    },
    {
      path: 'goods-table',
      component: () => import('@/views/table/goods-table'),
      name: 'GoodsTable',
      meta: { title: '物品管理' }
    },
    {
      path: 'recipients-table',
      component: () => import('@/views/table/recipients-table'),
      name: 'RecipientsTable',
      meta: { title: '领用物品管理' }
    },
    {
      path: 'non-Recipients-table',
      component: () => import('@/views/table/non-recipients-table'),
      name: 'NonRecipientsTable',
      meta: { title: '未领用物品管理' }
    }
  ]
}
export default tableRouter
