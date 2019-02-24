/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CompraComponentsPage, CompraDeleteDialog, CompraUpdatePage } from './compra.page-object';

const expect = chai.expect;

describe('Compra e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let compraUpdatePage: CompraUpdatePage;
    let compraComponentsPage: CompraComponentsPage;
    let compraDeleteDialog: CompraDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Compras', async () => {
        await navBarPage.goToEntity('compra');
        compraComponentsPage = new CompraComponentsPage();
        expect(await compraComponentsPage.getTitle()).to.eq('Compras');
    });

    it('should load create Compra page', async () => {
        await compraComponentsPage.clickOnCreateButton();
        compraUpdatePage = new CompraUpdatePage();
        expect(await compraUpdatePage.getPageTitle()).to.eq('Create or edit a Compra');
        await compraUpdatePage.cancel();
    });

    it('should create and save Compras', async () => {
        const nbButtonsBeforeCreate = await compraComponentsPage.countDeleteButtons();

        await compraComponentsPage.clickOnCreateButton();
        await promise.all([
            compraUpdatePage.setFechaInput('2000-12-31'),
            compraUpdatePage.setGuiaRemisionInput('guiaRemision'),
            compraUpdatePage.setNumeroDeDocumentoInput('numeroDeDocumento'),
            compraUpdatePage.ubicacionSelectLastOption(),
            compraUpdatePage.setMontoTotalInput('5'),
            compraUpdatePage.setCorrelativoInput('correlativo'),
            compraUpdatePage.tipoDePagoDeCompraSelectLastOption(),
            compraUpdatePage.tipoDeTransaccionSelectLastOption(),
            compraUpdatePage.estatusSelectLastOption(),
            compraUpdatePage.setMetaDataInput('metaData'),
            compraUpdatePage.proveedorSelectLastOption(),
            compraUpdatePage.tipoDeDocumentoDeCompraSelectLastOption(),
            compraUpdatePage.estatusDeCompraSelectLastOption(),
            compraUpdatePage.cajaSelectLastOption()
            // compraUpdatePage.productosSelectLastOption(),
        ]);
        expect(await compraUpdatePage.getFechaInput()).to.eq('2000-12-31');
        expect(await compraUpdatePage.getGuiaRemisionInput()).to.eq('guiaRemision');
        expect(await compraUpdatePage.getNumeroDeDocumentoInput()).to.eq('numeroDeDocumento');
        expect(await compraUpdatePage.getMontoTotalInput()).to.eq('5');
        expect(await compraUpdatePage.getCorrelativoInput()).to.eq('correlativo');
        expect(await compraUpdatePage.getMetaDataInput()).to.eq('metaData');
        await compraUpdatePage.save();
        expect(await compraUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await compraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Compra', async () => {
        const nbButtonsBeforeDelete = await compraComponentsPage.countDeleteButtons();
        await compraComponentsPage.clickOnLastDeleteButton();

        compraDeleteDialog = new CompraDeleteDialog();
        expect(await compraDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Compra?');
        await compraDeleteDialog.clickOnConfirmButton();

        expect(await compraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
