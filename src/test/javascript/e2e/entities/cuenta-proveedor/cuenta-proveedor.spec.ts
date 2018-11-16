/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CuentaProveedorComponentsPage, CuentaProveedorDeleteDialog, CuentaProveedorUpdatePage } from './cuenta-proveedor.page-object';

const expect = chai.expect;

describe('CuentaProveedor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let cuentaProveedorUpdatePage: CuentaProveedorUpdatePage;
    let cuentaProveedorComponentsPage: CuentaProveedorComponentsPage;
    let cuentaProveedorDeleteDialog: CuentaProveedorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CuentaProveedors', async () => {
        await navBarPage.goToEntity('cuenta-proveedor');
        cuentaProveedorComponentsPage = new CuentaProveedorComponentsPage();
        expect(await cuentaProveedorComponentsPage.getTitle()).to.eq('Cuenta Proveedors');
    });

    it('should load create CuentaProveedor page', async () => {
        await cuentaProveedorComponentsPage.clickOnCreateButton();
        cuentaProveedorUpdatePage = new CuentaProveedorUpdatePage();
        expect(await cuentaProveedorUpdatePage.getPageTitle()).to.eq('Create or edit a Cuenta Proveedor');
        await cuentaProveedorUpdatePage.cancel();
    });

    it('should create and save CuentaProveedors', async () => {
        const nbButtonsBeforeCreate = await cuentaProveedorComponentsPage.countDeleteButtons();

        await cuentaProveedorComponentsPage.clickOnCreateButton();
        await promise.all([
            cuentaProveedorUpdatePage.setCodigoInput('codigo'),
            cuentaProveedorUpdatePage.estatusSelectLastOption(),
            cuentaProveedorUpdatePage.setBancoInput('banco'),
            cuentaProveedorUpdatePage.setNombreCuentaInput('nombreCuenta'),
            cuentaProveedorUpdatePage.setNumeroDeCuentaInput('5'),
            cuentaProveedorUpdatePage.setFechaInput('2000-12-31')
        ]);
        expect(await cuentaProveedorUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await cuentaProveedorUpdatePage.getBancoInput()).to.eq('banco');
        expect(await cuentaProveedorUpdatePage.getNombreCuentaInput()).to.eq('nombreCuenta');
        expect(await cuentaProveedorUpdatePage.getNumeroDeCuentaInput()).to.eq('5');
        expect(await cuentaProveedorUpdatePage.getFechaInput()).to.eq('2000-12-31');
        await cuentaProveedorUpdatePage.save();
        expect(await cuentaProveedorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await cuentaProveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CuentaProveedor', async () => {
        const nbButtonsBeforeDelete = await cuentaProveedorComponentsPage.countDeleteButtons();
        await cuentaProveedorComponentsPage.clickOnLastDeleteButton();

        cuentaProveedorDeleteDialog = new CuentaProveedorDeleteDialog();
        expect(await cuentaProveedorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Cuenta Proveedor?');
        await cuentaProveedorDeleteDialog.clickOnConfirmButton();

        expect(await cuentaProveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
