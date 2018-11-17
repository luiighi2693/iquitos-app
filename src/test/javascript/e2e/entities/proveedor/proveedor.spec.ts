/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProveedorComponentsPage, ProveedorDeleteDialog, ProveedorUpdatePage } from './proveedor.page-object';

const expect = chai.expect;

describe('Proveedor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let proveedorUpdatePage: ProveedorUpdatePage;
    let proveedorComponentsPage: ProveedorComponentsPage;
    let proveedorDeleteDialog: ProveedorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Proveedors', async () => {
        await navBarPage.goToEntity('proveedor');
        proveedorComponentsPage = new ProveedorComponentsPage();
        expect(await proveedorComponentsPage.getTitle()).to.eq('Proveedors');
    });

    it('should load create Proveedor page', async () => {
        await proveedorComponentsPage.clickOnCreateButton();
        proveedorUpdatePage = new ProveedorUpdatePage();
        expect(await proveedorUpdatePage.getPageTitle()).to.eq('Create or edit a Proveedor');
        await proveedorUpdatePage.cancel();
    });

    it('should create and save Proveedors', async () => {
        const nbButtonsBeforeCreate = await proveedorComponentsPage.countDeleteButtons();

        await proveedorComponentsPage.clickOnCreateButton();
        await promise.all([
            proveedorUpdatePage.setCodigoInput('codigo'),
            proveedorUpdatePage.setRazonSocialInput('razonSocial'),
            proveedorUpdatePage.setDireccionInput('direccion'),
            proveedorUpdatePage.setCorreoInput('correo'),
            proveedorUpdatePage.setTelefonoInput('telefono'),
            proveedorUpdatePage.setSectorInput('sector')
            // proveedorUpdatePage.cuentaProveedorSelectLastOption(),
            // proveedorUpdatePage.contactoProveedorSelectLastOption(),
        ]);
        expect(await proveedorUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await proveedorUpdatePage.getRazonSocialInput()).to.eq('razonSocial');
        expect(await proveedorUpdatePage.getDireccionInput()).to.eq('direccion');
        expect(await proveedorUpdatePage.getCorreoInput()).to.eq('correo');
        expect(await proveedorUpdatePage.getTelefonoInput()).to.eq('telefono');
        expect(await proveedorUpdatePage.getSectorInput()).to.eq('sector');
        await proveedorUpdatePage.save();
        expect(await proveedorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await proveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Proveedor', async () => {
        const nbButtonsBeforeDelete = await proveedorComponentsPage.countDeleteButtons();
        await proveedorComponentsPage.clickOnLastDeleteButton();

        proveedorDeleteDialog = new ProveedorDeleteDialog();
        expect(await proveedorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Proveedor?');
        await proveedorDeleteDialog.clickOnConfirmButton();

        expect(await proveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
