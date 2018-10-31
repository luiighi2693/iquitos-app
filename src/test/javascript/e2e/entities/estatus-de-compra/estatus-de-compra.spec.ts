/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EstatusDeCompraComponentsPage, EstatusDeCompraDeleteDialog, EstatusDeCompraUpdatePage } from './estatus-de-compra.page-object';

const expect = chai.expect;

describe('EstatusDeCompra e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let estatusDeCompraUpdatePage: EstatusDeCompraUpdatePage;
    let estatusDeCompraComponentsPage: EstatusDeCompraComponentsPage;
    let estatusDeCompraDeleteDialog: EstatusDeCompraDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load EstatusDeCompras', async () => {
        await navBarPage.goToEntity('estatus-de-compra');
        estatusDeCompraComponentsPage = new EstatusDeCompraComponentsPage();
        expect(await estatusDeCompraComponentsPage.getTitle()).to.eq('Estatus De Compras');
    });

    it('should load create EstatusDeCompra page', async () => {
        await estatusDeCompraComponentsPage.clickOnCreateButton();
        estatusDeCompraUpdatePage = new EstatusDeCompraUpdatePage();
        expect(await estatusDeCompraUpdatePage.getPageTitle()).to.eq('Create or edit a Estatus De Compra');
        await estatusDeCompraUpdatePage.cancel();
    });

    it('should create and save EstatusDeCompras', async () => {
        const nbButtonsBeforeCreate = await estatusDeCompraComponentsPage.countDeleteButtons();

        await estatusDeCompraComponentsPage.clickOnCreateButton();
        await promise.all([estatusDeCompraUpdatePage.setNombreInput('nombre')]);
        expect(await estatusDeCompraUpdatePage.getNombreInput()).to.eq('nombre');
        await estatusDeCompraUpdatePage.save();
        expect(await estatusDeCompraUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await estatusDeCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last EstatusDeCompra', async () => {
        const nbButtonsBeforeDelete = await estatusDeCompraComponentsPage.countDeleteButtons();
        await estatusDeCompraComponentsPage.clickOnLastDeleteButton();

        estatusDeCompraDeleteDialog = new EstatusDeCompraDeleteDialog();
        expect(await estatusDeCompraDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Estatus De Compra?');
        await estatusDeCompraDeleteDialog.clickOnConfirmButton();

        expect(await estatusDeCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
