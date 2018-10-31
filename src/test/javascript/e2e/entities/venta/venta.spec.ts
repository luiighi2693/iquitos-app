/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VentaComponentsPage, VentaDeleteDialog, VentaUpdatePage } from './venta.page-object';

const expect = chai.expect;

describe('Venta e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let ventaUpdatePage: VentaUpdatePage;
    let ventaComponentsPage: VentaComponentsPage;
    let ventaDeleteDialog: VentaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Ventas', async () => {
        await navBarPage.goToEntity('venta');
        ventaComponentsPage = new VentaComponentsPage();
        expect(await ventaComponentsPage.getTitle()).to.eq('Ventas');
    });

    it('should load create Venta page', async () => {
        await ventaComponentsPage.clickOnCreateButton();
        ventaUpdatePage = new VentaUpdatePage();
        expect(await ventaUpdatePage.getPageTitle()).to.eq('Create or edit a Venta');
        await ventaUpdatePage.cancel();
    });

    it('should create and save Ventas', async () => {
        const nbButtonsBeforeCreate = await ventaComponentsPage.countDeleteButtons();

        await ventaComponentsPage.clickOnCreateButton();
        await promise.all([
            ventaUpdatePage.setCodigoInput('codigo'),
            ventaUpdatePage.setSubTotalInput('5'),
            ventaUpdatePage.setImpuestoInput('5'),
            ventaUpdatePage.setMontoTotalInput('5'),
            ventaUpdatePage.setFechaInput('2000-12-31'),
            ventaUpdatePage.estatusSelectLastOption(),
            ventaUpdatePage.setGlosaInput('glosa'),
            ventaUpdatePage.setMetaDataInput('metaData'),
            ventaUpdatePage.clienteSelectLastOption(),
            ventaUpdatePage.empleadoSelectLastOption(),
            ventaUpdatePage.cajaSelectLastOption(),
            ventaUpdatePage.tipoDeDocumentoDeVentaSelectLastOption(),
            ventaUpdatePage.tipoDePagoSelectLastOption(),
            ventaUpdatePage.estatusDeProductoEntregadoSelectLastOption()
            // ventaUpdatePage.productosSelectLastOption(),
        ]);
        expect(await ventaUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await ventaUpdatePage.getSubTotalInput()).to.eq('5');
        expect(await ventaUpdatePage.getImpuestoInput()).to.eq('5');
        expect(await ventaUpdatePage.getMontoTotalInput()).to.eq('5');
        expect(await ventaUpdatePage.getFechaInput()).to.eq('2000-12-31');
        expect(await ventaUpdatePage.getGlosaInput()).to.eq('glosa');
        expect(await ventaUpdatePage.getMetaDataInput()).to.eq('metaData');
        await ventaUpdatePage.save();
        expect(await ventaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await ventaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Venta', async () => {
        const nbButtonsBeforeDelete = await ventaComponentsPage.countDeleteButtons();
        await ventaComponentsPage.clickOnLastDeleteButton();

        ventaDeleteDialog = new VentaDeleteDialog();
        expect(await ventaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Venta?');
        await ventaDeleteDialog.clickOnConfirmButton();

        expect(await ventaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
