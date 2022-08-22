import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '商品名称',
    align: 'left',
    dataIndex: 'name'
   },
   {
    title: '价格',
    align: 'center',
    dataIndex: 'price'
   },
   {
    title: '商品描述',
    align: 'center',
    dataIndex: 'spDesc'
   },
   {
    title: '上架日期',
    align: 'center',
    dataIndex: 'sjData',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text)
    },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '父级节点',
    field: 'pid',
    component: 'JTreeSelect',
    componentProps: {
      dict: "test_tree,name,id",
      pidField: "pid",
      pidValue: "0",
      hasChildField: "has_child",
    },
  },
  {
    label: '商品名称',
    field: 'name',
    component: 'Input',
  },
  {
    label: '价格',
    field: 'price',
    component: 'Input',
  },
  {
    label: '商品描述',
    field: 'spDesc',
    component: 'Input',
  },
  {
    label: '上架日期',
    field: 'sjData',
    component: 'DatePicker',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];
