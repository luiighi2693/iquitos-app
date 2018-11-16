/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ContactoProveedorComponentsPage,
    ContactoProveedorDeleteDialog,
    ContactoProveedorUpdatePage
} from './contacto-proveedor.page-object';

const expect = chai.expect;

describe('ContactoProveedor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactoProveedorUpdatePage: ContactoProveedorUpdatePage;
    let contactoProveedorComponentsPage: ContactoProveedorComponentsPage;
    let contactoProveedorDeleteDialog: ContactoProveedorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContactoProveedors', async () => {
        await navBarPage.goToEntity('contacto-proveedor');
        contactoProveedorComponentsPage = new ContactoProveedorComponentsPage();
        expect(await contactoProveedorComponentsPage.getTitle()).to.eq('Contacto Proveedors');
    });

    it('should load create ContactoProveedor page', async () => {
        await contactoProveedorComponentsPage.clickOnCreateButton();
        contactoProveedorUpdatePage = new ContactoProveedorUpdatePage();
        expect(await contactoProveedorUpdatePage.getPageTitle()).to.eq('Create or edit a Contacto Proveedor');
        await contactoProveedorUpdatePage.cancel();
    });

    it('should create and save ContactoProveedors', async () => {
        const nbButtonsBeforeCreate = await contactoProveedorComponentsPage.countDeleteButtons();

        await contactoProveedorComponentsPage.clickOnCreateButton();
        await promise.all([
            contactoProveedorUpdatePage.setNombreInput('nombre'),
            contactoProveedorUpdatePage.setCargoInput('cargo'),
            contactoProveedorUpdatePage.setProductoInput('producto'),
            contactoProveedorUpdatePage.setTelefonoInput('5')
        ]);
        expect(await contactoProveedorUpdatePage.getNombreInput()).to.eq('nombre');
        expect(await contactoProveedorUpdatePage.getCargoInput()).to.eq('cargo');
        expect(await contactoProveedorUpdatePage.getProductoInput()).to.eq('producto');
        expect(await contactoProveedorUpdatePage.getTelefonoInput()).to.eq('5');
        await contactoProveedorUpdatePage.save();
        expect(await contactoProveedorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactoProveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ContactoProveedor', async () => {
        const nbButtonsBeforeDelete = await contactoProveedorComponentsPage.countDeleteButtons();
        await contactoProveedorComponentsPage.clickOnLastDeleteButton();

        contactoProveedorDeleteDialog = new ContactoProveedorDeleteDialog();
        expect(await contactoProveedorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Contacto Proveedor?');
        await contactoProveedorDeleteDialog.clickOnConfirmButton();

        expect(await contactoProveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
