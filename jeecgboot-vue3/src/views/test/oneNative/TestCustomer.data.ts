import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '姓名',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '性别',
    align: "center",
    dataIndex: 'sex'
  },
  {
    title: '年龄',
    align: "center",
    dataIndex: 'age'
  },
  {
    title: '生日',
    align: "center",
    dataIndex: 'birthday',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text);
    },
  },
  {
    title: '家庭地址',
    align: "center",
    dataIndex: 'address'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '姓名',
    field: 'name',
    component: 'Input',
  },
  {
    label: '性别',
    field: 'sex',
    component: 'Input',
  },
  {
    label: '年龄',
    field: 'age',
    component: 'Input',
  },
  {
    label: '生日',
    field: 'birthday',
    component: 'DatePicker',
  },
  {
    label: '家庭地址',
    field: 'address',
    component: 'InputTextArea',
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
