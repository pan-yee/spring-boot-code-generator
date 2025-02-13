<script lang="ts" setup>

import { ref, reactive } from 'vue'
import Api from '@/api'
import { Search, CirclePlus, Edit, Delete, More, View, Operation, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox, ElTree } from 'element-plus'
import Color from 'element-plus/es/components/color-picker/src/utils/color';
import { useRouter } from 'vue-router'

const router = useRouter()

var Data = reactive({
    list: [],
    currentPage: ref(1),
    pageSize: ref(10),
    totalRecords: ref(100),
    searchForm: ref({
        id: '',
        warehouseCode: '',
        warehouseName: '',
        tenantId: '',
        countryId: '',
        countryName: '',
        provinceId: '',
        provinceName: '',
        cityId: '',
        cityName: '',
        districtId: '',
        districtName: '',
        address: '',
        contactPerson: '',
        mobile: '',
        mobileLast: '',
        contactLandline: '',
        notes: '',
        status: '',
        isDeleted: '',
        createTime: '',
        updateTime: '',
        updateUser: '',
        createUser: '',
        orgName: '',
        countryAreaCode: ''
    }),
    editForm: ref({
        id: '',
        warehouseCode: '',
        warehouseName: '',
        tenantId: '',
        countryId: '',
        countryName: '',
        provinceId: '',
        provinceName: '',
        cityId: '',
        cityName: '',
        districtId: '',
        districtName: '',
        address: '',
        contactPerson: '',
        mobile: '',
        mobileLast: '',
        contactLandline: '',
        notes: '',
        status: '',
        isDeleted: '',
        createTime: '',
        updateTime: '',
        updateUser: '',
        createUser: '',
        orgName: '',
        countryAreaCode: ''
    }),
    editDialogVisible: ref(false),
    detailDialogVisible: ref(false),
    dialogTitle: ref("详情"),
    warehouseStatusSelectList: ref([{ name: '启用', value: '1' }, { name: '禁用', value: '0' }]),
    defaultProps: {
        children: 'children',
        label: 'menuName',
        disabled: 'disabled',
    },
    defaultCheckedList: ref([])
})

const queryPage = () => {
    let params = {
        page: Data.currentPage,
        limit: Data.pageSize,
        id: Data.searchForm.id,
        warehouseCode: Data.searchForm.warehouseCode,
        warehouseName: Data.searchForm.warehouseName,
        tenantId: Data.searchForm.tenantId,
        countryId: Data.searchForm.countryId,
        countryName: Data.searchForm.countryName,
        provinceId: Data.searchForm.provinceId,
        provinceName: Data.searchForm.provinceName,
        cityId: Data.searchForm.cityId,
        cityName: Data.searchForm.cityName,
        districtId: Data.searchForm.districtId,
        districtName: Data.searchForm.districtName,
        address: Data.searchForm.address,
        contactPerson: Data.searchForm.contactPerson,
        mobile: Data.searchForm.mobile,
        mobileLast: Data.searchForm.mobileLast,
        contactLandline: Data.searchForm.contactLandline,
        notes: Data.searchForm.notes,
        status: Data.searchForm.status,
        isDeleted: Data.searchForm.isDeleted,
        createTime: Data.searchForm.createTime,
        updateTime: Data.searchForm.updateTime,
        updateUser: Data.searchForm.updateUser,
        createUser: Data.searchForm.createUser,
        orgName: Data.searchForm.orgName,
        countryAreaCode: Data.searchForm.countryAreaCode
    }
    Api.WarehouseApi.commonQueryPage(params).then((res) => {
        if (res.status = 200) {
            Data.list = res.data.data.records;
            Data.currentPage = Number.parseInt(res.data.data.current);
            Data.pageSize = Number.parseInt(res.data.data.size);
            Data.totalRecords = Number.parseInt(res.data.data.total);
            successMessage();
        } else {
            errorMessage();
        }
    }).catch((err) => {
        console.log(err);
    });
};

const autoRun = () => {
    queryPage();
};

autoRun();

const handleSizeChange = (pageSize: number) => {
    console.log(`\$\{pageSize\} items per page`)
}

const handleCurrentChange = (currentPage: number) => {
    console.log(`current page: \$\{currentPage\}`)
    if (Data.currentPage != currentPage) {
        Data.currentPage = currentPage
        queryPage();
    }
}

const dialogAddForm = () => {
    Data.dialogTitle = "添加"
    Data.editDialogVisible = true
    Data.detailDialogVisible = false
    Data.searchForm.id = ''
    Data.searchForm.warehouseCode = ''
    Data.searchForm.warehouseName = ''
    Data.searchForm.tenantId = ''
    Data.searchForm.countryId = ''
    Data.searchForm.countryName = ''
    Data.searchForm.provinceId = ''
    Data.searchForm.provinceName = ''
    Data.searchForm.cityId = ''
    Data.searchForm.cityName = ''
    Data.searchForm.districtId = ''
    Data.searchForm.districtName = ''
    Data.searchForm.address = ''
    Data.searchForm.contactPerson = ''
    Data.searchForm.mobile = ''
    Data.searchForm.mobileLast = ''
    Data.searchForm.contactLandline = ''
    Data.searchForm.notes = ''
    Data.searchForm.status = ''
    Data.searchForm.isDeleted = ''
    Data.searchForm.createTime = ''
    Data.searchForm.updateTime = ''
    Data.searchForm.updateUser = ''
    Data.searchForm.createUser = ''
    Data.searchForm.orgName = ''
    Data.searchForm.countryAreaCode = ''
}

const viewDetail = (id: any) => {
    Data.dialogTitle = "详情";
    Data.editDialogVisible = false;
    Data.detailDialogVisible = true
    queryDetail(id);
}

const viewEdit = (id: any) => {
    Data.dialogTitle = "编辑";
    Data.editDialogVisible = true;
    Data.detailDialogVisible = false
    queryDetail(id);
}

const queryDetail = (id: any) => {
    let params = {
        id: id
    }
    Api.WarehouseApi.commonQueryDetail(params).then((res) => {
        console.log(res);
        if (res.status = 200) {
            Data.editForm = res.data.data
            successMessage();
        } else {
            errorMessage();
        }
    }).catch((err) => {
        console.log(err);
    });
};

const deleteRow = (id: any) => {
    ElMessageBox.confirm("确认要删除数据?").then(() => {
        let params = {
            id: id,
        }
        Api.WarehouseApi.commonDelete(params).then((res) => {
            console.log(res);
            if (res.status = 200) {
                Data.dialogTitle = "";
                Data.editDialogVisible = false;
                queryPage();
            } else {
                errorMessage();
            }
        }).catch((err) => {
            console.log(err);
            errorMessage();
        });
    })
}

const updateStatus = (id: any, status: any) => {
    let newStatus = status == '1' ? '0' : '1';
    let params = {
        id: id,
        warehouseStatus: newStatus,
    }
    Api.WarehouseApi.commonUpdateStatus(params).then((res) => {
        console.log(res);
        if (res.status = 200) {
            queryPage();
        } else {
            errorMessage();
        }
    }).catch((err) => {
        console.log(err);
        errorMessage();
    });
}

const submitForm = () => {
    let params = {
        id: Data.editForm.id,
        warehouseCode: Data.editForm.warehouseCode,
        warehouseName: Data.editForm.warehouseName,
        tenantId: Data.editForm.tenantId,
        countryId: Data.editForm.countryId,
        countryName: Data.editForm.countryName,
        provinceId: Data.editForm.provinceId,
        provinceName: Data.editForm.provinceName,
        cityId: Data.editForm.cityId,
        cityName: Data.editForm.cityName,
        districtId: Data.editForm.districtId,
        districtName: Data.editForm.districtName,
        address: Data.editForm.address,
        contactPerson: Data.editForm.contactPerson,
        mobile: Data.editForm.mobile,
        mobileLast: Data.editForm.mobileLast,
        contactLandline: Data.editForm.contactLandline,
        notes: Data.editForm.notes,
        status: Data.editForm.status,
        isDeleted: Data.editForm.isDeleted,
        createTime: Data.editForm.createTime,
        updateTime: Data.editForm.updateTime,
        updateUser: Data.editForm.updateUser,
        createUser: Data.editForm.createUser,
        orgName: Data.editForm.orgName,
        countryAreaCode: Data.editForm.countryAreaCode,
    }
    if (Data.editForm.id) {
        Api.WarehouseApi.commonUpdate(params).then((res) => {
            console.log(res);
            if (res.status = 200) {
                Data.dialogTitle = "";
                closeDialog();
                queryPage();
            } else {
                errorMessage();
            }
        }).catch((err) => {
            console.log(err);
            errorMessage();
        });
    } else {
        Api.WarehouseApi.commonSave(params).then((res) => {
            if (res.status = 200) {
                Data.dialogTitle = "";
                closeDialog();
                queryPage();
            } else {
                errorMessage();
            }
        }).catch((err) => {
            console.log(err);
            errorMessage();
        });
    }
}

const closeDialog = () => {
    Data.editDialogVisible = false
    Data.detailDialogVisible = false
    Data.roleUsersDialogVisible = false
}

const errorMessage = () => {
    ElMessage.error('数据加载失败')
}

const successMessage = () => {
    ElMessage({
        message: "数据加载成功",
        type: "success"
    })
}

</script>

<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>

<template>
    <el-form :inline="true" style="display: flex;">
        <el-form-item label="主键id">
            <el-input v-model="Data.searchForm.id" clearable placeholder="请输入主键id"></el-input>
        </el-form-item>
        <el-form-item label="仓库编码">
            <el-input v-model="Data.searchForm.warehouseCode" clearable placeholder="请输入仓库编码"></el-input>
        </el-form-item>
        <el-form-item label="仓库名称">
            <el-input v-model="Data.searchForm.warehouseName" clearable placeholder="请输入仓库名称"></el-input>
        </el-form-item>
        <el-form-item label="租户ID">
            <el-input v-model="Data.searchForm.tenantId" clearable placeholder="请输入租户ID"></el-input>
        </el-form-item>
        <el-form-item label="国家id">
            <el-input v-model="Data.searchForm.countryId" clearable placeholder="请输入国家id"></el-input>
        </el-form-item>
        <el-form-item label="国家名称">
            <el-input v-model="Data.searchForm.countryName" clearable placeholder="请输入国家名称"></el-input>
        </el-form-item>
        <el-form-item label="省份id">
            <el-input v-model="Data.searchForm.provinceId" clearable placeholder="请输入省份id"></el-input>
        </el-form-item>
        <el-form-item label="省名称">
            <el-input v-model="Data.searchForm.provinceName" clearable placeholder="请输入省名称"></el-input>
        </el-form-item>
        <el-form-item label="城市id">
            <el-input v-model="Data.searchForm.cityId" clearable placeholder="请输入城市id"></el-input>
        </el-form-item>
        <el-form-item label="市名称">
            <el-input v-model="Data.searchForm.cityName" clearable placeholder="请输入市名称"></el-input>
        </el-form-item>
        <el-form-item label="区县id">
            <el-input v-model="Data.searchForm.districtId" clearable placeholder="请输入区县id"></el-input>
        </el-form-item>
        <el-form-item label="区县名称">
            <el-input v-model="Data.searchForm.districtName" clearable placeholder="请输入区县名称"></el-input>
        </el-form-item>
        <el-form-item label="详细地址">
            <el-input v-model="Data.searchForm.address" clearable placeholder="请输入详细地址"></el-input>
        </el-form-item>
        <el-form-item label="联系人">
            <el-input v-model="Data.searchForm.contactPerson" clearable placeholder="请输入联系人"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
            <el-input v-model="Data.searchForm.mobile" clearable placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="电话后4为">
            <el-input v-model="Data.searchForm.mobileLast" clearable placeholder="请输入电话后4为"></el-input>
        </el-form-item>
        <el-form-item label="固话">
            <el-input v-model="Data.searchForm.contactLandline" clearable placeholder="请输入固话"></el-input>
        </el-form-item>
        <el-form-item label="备注">
            <el-input v-model="Data.searchForm.notes" clearable placeholder="请输入备注"></el-input>
        </el-form-item>
        <el-form-item label="状态:0:停用、1:正常">
            <el-input v-model="Data.searchForm.status" clearable placeholder="请输入状态:0:停用、1:正常"></el-input>
        </el-form-item>
        <el-form-item label="删除状态：0->正常；1->删除">
            <el-input v-model="Data.searchForm.isDeleted" clearable placeholder="请输入删除状态：0->正常；1->删除"></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
            <el-input v-model="Data.searchForm.createTime" clearable placeholder="请输入创建时间"></el-input>
        </el-form-item>
        <el-form-item label="更新时间">
            <el-input v-model="Data.searchForm.updateTime" clearable placeholder="请输入更新时间"></el-input>
        </el-form-item>
        <el-form-item label="修改用户id">
            <el-input v-model="Data.searchForm.updateUser" clearable placeholder="请输入修改用户id"></el-input>
        </el-form-item>
        <el-form-item label="创建用户id">
            <el-input v-model="Data.searchForm.createUser" clearable placeholder="请输入创建用户id"></el-input>
        </el-form-item>
        <el-form-item label="所属组织名称">
            <el-input v-model="Data.searchForm.orgName" clearable placeholder="请输入所属组织名称"></el-input>
        </el-form-item>
        <el-form-item label="国家区域代码">
            <el-input v-model="Data.searchForm.countryAreaCode" clearable placeholder="请输入国家区域代码"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" :icon="Search" @click="queryPage">查询</el-button>
            <el-button type="primary" :icon="CirclePlus" @click="dialogAddForm">添加</el-button>
        </el-form-item>
    </el-form>

    <el-table :data="Data.list" border stripe style="width:100%"
            :header-cell-style="{ backgroundColor: '#409EFF', color: '#FFF', fontSize: '14px' }">
        <el-table-column label="序号" type="index" width="60px" align="center"></el-table-column>
        <el-table-column label="主键id" prop="id"></el-table-column>
        <el-table-column label="仓库编码" prop="warehouseCode"></el-table-column>
        <el-table-column label="仓库名称" prop="warehouseName"></el-table-column>
        <el-table-column label="租户ID" prop="tenantId"></el-table-column>
        <el-table-column label="国家id" prop="countryId"></el-table-column>
        <el-table-column label="国家名称" prop="countryName"></el-table-column>
        <el-table-column label="省份id" prop="provinceId"></el-table-column>
        <el-table-column label="省名称" prop="provinceName"></el-table-column>
        <el-table-column label="城市id" prop="cityId"></el-table-column>
        <el-table-column label="市名称" prop="cityName"></el-table-column>
        <el-table-column label="区县id" prop="districtId"></el-table-column>
        <el-table-column label="区县名称" prop="districtName"></el-table-column>
        <el-table-column label="详细地址" prop="address"></el-table-column>
        <el-table-column label="联系人" prop="contactPerson"></el-table-column>
        <el-table-column label="手机号" prop="mobile"></el-table-column>
        <el-table-column label="电话后4为" prop="mobileLast"></el-table-column>
        <el-table-column label="固话" prop="contactLandline"></el-table-column>
        <el-table-column label="备注" prop="notes"></el-table-column>
        <el-table-column label="状态:0:停用、1:正常" prop="status"></el-table-column>
        <el-table-column label="删除状态：0->正常；1->删除" prop="isDeleted"></el-table-column>
        <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="更新时间" prop="updateTime"></el-table-column>
        <el-table-column label="修改用户id" prop="updateUser"></el-table-column>
        <el-table-column label="创建用户id" prop="createUser"></el-table-column>
        <el-table-column label="所属组织名称" prop="orgName"></el-table-column>
        <el-table-column label="国家区域代码" prop="countryAreaCode"></el-table-column>
        <el-table-column label="操作">
            <template #default="scope">
                <el-button @click="viewDetail(scope.row.id)" type="primary" :icon="View" circle title="详情"></el-button>
                <el-button @click="viewEdit(scope.row.id)" type="warning" :icon="Edit" circle title="编辑"></el-button>
                <el-button @click="deleteRow(scope.row.id)" type="danger" :icon="Delete" circle title="删除"></el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-pagination background style="margin-top: 20px;" v-model:current-page="Data.currentPage"
            v-model:page-size="Data.pageSize" :total="Data.totalRecords" :page-sizes="[5, 10, 15, 50]"
            layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />

    <el-dialog v-model="Data.editDialogVisible" width="30%" :show-close="false" draggable>
        <template #header>
            <div style="font-size: 18px; color: #409eff; font-weight: bold; text-align: left;">{{ Data.dialogTitle }}
            </div>
        </template>
        <el-form>
            <el-form-item label="主键id" label-width="120px">
                <el-input v-model="Data.editForm.id" clearable placeholder="请输入主键id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="仓库编码" label-width="120px">
                <el-input v-model="Data.editForm.warehouseCode" clearable placeholder="请输入仓库编码" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="仓库名称" label-width="120px">
                <el-input v-model="Data.editForm.warehouseName" clearable placeholder="请输入仓库名称" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="租户ID" label-width="120px">
                <el-input v-model="Data.editForm.tenantId" clearable placeholder="请输入租户ID" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="国家id" label-width="120px">
                <el-input v-model="Data.editForm.countryId" clearable placeholder="请输入国家id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="国家名称" label-width="120px">
                <el-input v-model="Data.editForm.countryName" clearable placeholder="请输入国家名称" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="省份id" label-width="120px">
                <el-input v-model="Data.editForm.provinceId" clearable placeholder="请输入省份id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="省名称" label-width="120px">
                <el-input v-model="Data.editForm.provinceName" clearable placeholder="请输入省名称" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="城市id" label-width="120px">
                <el-input v-model="Data.editForm.cityId" clearable placeholder="请输入城市id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="市名称" label-width="120px">
                <el-input v-model="Data.editForm.cityName" clearable placeholder="请输入市名称" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="区县id" label-width="120px">
                <el-input v-model="Data.editForm.districtId" clearable placeholder="请输入区县id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="区县名称" label-width="120px">
                <el-input v-model="Data.editForm.districtName" clearable placeholder="请输入区县名称" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="详细地址" label-width="120px">
                <el-input v-model="Data.editForm.address" clearable placeholder="请输入详细地址" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="联系人" label-width="120px">
                <el-input v-model="Data.editForm.contactPerson" clearable placeholder="请输入联系人" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="手机号" label-width="120px">
                <el-input v-model="Data.editForm.mobile" clearable placeholder="请输入手机号" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="电话后4为" label-width="120px">
                <el-input v-model="Data.editForm.mobileLast" clearable placeholder="请输入电话后4为" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="固话" label-width="120px">
                <el-input v-model="Data.editForm.contactLandline" clearable placeholder="请输入固话" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="备注" label-width="120px">
                <el-input v-model="Data.editForm.notes" clearable placeholder="请输入备注" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="状态:0:停用、1:正常" label-width="120px">
                <el-input v-model="Data.editForm.status" clearable placeholder="请输入状态:0:停用、1:正常" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="删除状态：0->正常；1->删除" label-width="120px">
                <el-input v-model="Data.editForm.isDeleted" clearable placeholder="请输入删除状态：0->正常；1->删除" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" label-width="120px">
                <el-input v-model="Data.editForm.createTime" clearable placeholder="请输入创建时间" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" label-width="120px">
                <el-input v-model="Data.editForm.updateTime" clearable placeholder="请输入更新时间" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="修改用户id" label-width="120px">
                <el-input v-model="Data.editForm.updateUser" clearable placeholder="请输入修改用户id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="创建用户id" label-width="120px">
                <el-input v-model="Data.editForm.createUser" clearable placeholder="请输入创建用户id" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="所属组织名称" label-width="120px">
                <el-input v-model="Data.editForm.orgName" clearable placeholder="请输入所属组织名称" :suffix-icon="Edit"></el-input>
            </el-form-item>
            <el-form-item label="国家区域代码" label-width="120px">
                <el-input v-model="Data.editForm.countryAreaCode" clearable placeholder="请输入国家区域代码" :suffix-icon="Edit"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="closeDialog" type="primary">取消</el-button>
                <el-button @click="submitForm" type="primary">确定</el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog v-model="Data.detailDialogVisible" width="30%" :show-close="false" draggable>
        <template #header>
            <div style="font-size: 18px; color: #409eff; font-weight: bold; text-align: left;">{{ Data.dialogTitle }}
            </div>
        </template>
        <el-form>
            <el-form-item label="主键id" label-width="120px">
                <el-input v-model="Data.editForm.id" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="仓库编码" label-width="120px">
                <el-input v-model="Data.editForm.warehouseCode" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="仓库名称" label-width="120px">
                <el-input v-model="Data.editForm.warehouseName" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="租户ID" label-width="120px">
                <el-input v-model="Data.editForm.tenantId" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="国家id" label-width="120px">
                <el-input v-model="Data.editForm.countryId" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="国家名称" label-width="120px">
                <el-input v-model="Data.editForm.countryName" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="省份id" label-width="120px">
                <el-input v-model="Data.editForm.provinceId" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="省名称" label-width="120px">
                <el-input v-model="Data.editForm.provinceName" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="城市id" label-width="120px">
                <el-input v-model="Data.editForm.cityId" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="市名称" label-width="120px">
                <el-input v-model="Data.editForm.cityName" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="区县id" label-width="120px">
                <el-input v-model="Data.editForm.districtId" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="区县名称" label-width="120px">
                <el-input v-model="Data.editForm.districtName" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="详细地址" label-width="120px">
                <el-input v-model="Data.editForm.address" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="联系人" label-width="120px">
                <el-input v-model="Data.editForm.contactPerson" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="手机号" label-width="120px">
                <el-input v-model="Data.editForm.mobile" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="电话后4为" label-width="120px">
                <el-input v-model="Data.editForm.mobileLast" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="固话" label-width="120px">
                <el-input v-model="Data.editForm.contactLandline" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="备注" label-width="120px">
                <el-input v-model="Data.editForm.notes" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="状态:0:停用、1:正常" label-width="120px">
                <el-input v-model="Data.editForm.status" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="删除状态：0->正常；1->删除" label-width="120px">
                <el-input v-model="Data.editForm.isDeleted" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="创建时间" label-width="120px">
                <el-input v-model="Data.editForm.createTime" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="更新时间" label-width="120px">
                <el-input v-model="Data.editForm.updateTime" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="修改用户id" label-width="120px">
                <el-input v-model="Data.editForm.updateUser" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="创建用户id" label-width="120px">
                <el-input v-model="Data.editForm.createUser" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="所属组织名称" label-width="120px">
                <el-input v-model="Data.editForm.orgName" readonly disabled></el-input>
            </el-form-item>
            <el-form-item label="国家区域代码" label-width="120px">
                <el-input v-model="Data.editForm.countryAreaCode" readonly disabled></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="closeDialog" type="primary">取消</el-button>
            </span>
        </template>
    </el-dialog>
</template>