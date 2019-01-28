/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AmortizacionComponentsPage, AmortizacionDeleteDialog, AmortizacionUpdatePage } from './amortizacion.page-object';

const expect = chai.expect;

describe('Amortizacion e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let amortizacionUpdatePage: AmortizacionUpdatePage;
    let amortizacionComponentsPage: AmortizacionComponentsPage;
    let amortizacionDeleteDialog: AmortizacionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Amortizacions', async () => {
        await navBarPage.goToEntity('amortizacion');
        amortizacionComponentsPage = new AmortizacionComponentsPage();
        expect(await amortizacionComponentsPage.getTitle()).to.eq('Amortizacions');
    });

    it('should load create Amortizacion page', async () => {
        await amortizacionComponentsPage.clickOnCreateButton();
        amortizacionUpdatePage = new AmortizacionUpdatePage();
        expect(await amortizacionUpdatePage.getPageTitle()).to.eq('Create or edit a Amortizacion');
        await amortizacionUpdatePage.cancel();
    });

    it('should create and save Amortizacions', async () => {
        const nbButtonsBeforeCreate = await amortizacionComponentsPage.countDeleteButtons();

        await amortizacionComponentsPage.clickOnCreateButton();
        await promise.all([
            amortizacionUpdatePage.setCodigoInput('codigo'),
            amortizacionUpdatePage.setMontoInput('5'),
            amortizacionUpdatePage.setMontoPagadoInput('5'),
            amortizacionUpdatePage.setFechaInput('2000-12-31'),
            amortizacionUpdatePage.setCodigoDocumentoInput('codigoDocumento'),
            amortizacionUpdatePage.setGlosaInput('glosa'),
            amortizacionUpdatePage.tipoDeDocumentoDeVentaSelectLastOption(),
            amortizacionUpdatePage.tipoDePagoSelectLastOption()
        ]);
        expect(await amortizacionUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await amortizacionUpdatePage.getMontoInput()).to.eq('5');
        expect(await amortizacionUpdatePage.getMontoPagadoInput()).to.eq('5');
        expect(await amortizacionUpdatePage.getFechaInput()).to.eq('2000-12-31');
        expect(await amortizacionUpdatePage.getCodigoDocumentoInput()).to.eq('codigoDocumento');
        expect(await amortizacionUpdatePage.getGlosaInput()).to.eq('glosa');
        await amortizacionUpdatePage.save();
        expect(await amortizacionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await amortizacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Amortizacion', async () => {
        const nbButtonsBeforeDelete = await amortizacionComponentsPage.countDeleteButtons();
        await amortizacionComponentsPage.clickOnLastDeleteButton();

        amortizacionDeleteDialog = new AmortizacionDeleteDialog();
        expect(await amortizacionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Amortizacion?');
        await amortizacionDeleteDialog.clickOnConfirmButton();

        expect(await amortizacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
